package jdev.novid.component.asmapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Language;
import com.aerospike.client.Record;
import com.aerospike.client.Value;
import com.aerospike.client.policy.CommitLevel;
import com.aerospike.client.policy.InfoPolicy;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.Filter;
import com.aerospike.client.query.IndexType;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.ResultSet;
import com.aerospike.client.query.Statement;
import com.aerospike.client.task.RegisterTask;

public class AerospikeTemplate {

    private AerospikeClient aerospikeClient;
    private AerospikeMapper aerospikeMapper;
    private String namespace;

    public AerospikeTemplate(AerospikeClient aerospikeClient, String namespace) {

        this.aerospikeClient = aerospikeClient;
        this.aerospikeMapper = new AerospikeMapper();
        this.namespace = namespace;
    }

    public boolean createIndex(String name, String set, String bin, IndexType type) {

        try {

            this.aerospikeClient.createIndex(null, this.namespace, set, name, bin, type).waitTillComplete();

        } catch (AerospikeException e) {

            return false;
        }

        return true;
    }

    public void delete(Long pk, String set) {

        Key key = new Key(this.namespace, set, pk);

        WritePolicy writePolicy = new WritePolicy();
        writePolicy.commitLevel = CommitLevel.COMMIT_ALL;

        this.aerospikeClient.delete(writePolicy, key);
    }

    public <T> Optional<T> find(Long pk, String set, Class<T> template) {

        Key key = new Key(this.namespace, set, pk);

        Record record = aerospikeClient.get(null, key);

        if (record == null) {

            return Optional.empty();
        }

        T instance = aerospikeMapper.deserialize(record, template);

        return Optional.of(instance);
    }

    public <T> List<T> query(String set, Filter filter, Class<T> template) {

        Statement stm = new Statement();

        stm.setNamespace(this.namespace);
        stm.setSetName(set);

        if (filter != null)
            stm.setFilter(filter);

        RecordSet recordSet = this.aerospikeClient.query(null, stm);
        List<T> result = new ArrayList<T>();

        while (recordSet.next()) {

            Record record = recordSet.getRecord();
            result.add(this.aerospikeMapper.deserialize(record, template));
        }

        recordSet.close();

        return result;

    }

    @SuppressWarnings("unchecked")
    public <T> List<T> queryAggregate(String set, ClassLoader resourceLoader, String resourcePath, Filter filter,
            Class<T> template, String packageName, String functionName, Value... functionArgs) {

        Statement stm = new Statement();

        stm.setNamespace(this.namespace);
        stm.setSetName(set);
        stm.setAggregateFunction(resourceLoader, resourcePath, packageName, functionName, functionArgs);

        if (filter != null)
            stm.setFilter(filter);

        ResultSet resultSet = this.aerospikeClient.queryAggregate(null, stm, packageName, functionName, functionArgs);

        List<T> result = new ArrayList<T>();

        while (resultSet.next()) {

            Object object = resultSet.getObject();

            if (object instanceof Map) {
                result.add(this.aerospikeMapper.deserialize((Map<String, Object>) object, template));
            }
        }

        resultSet.close();

        return result;

    }

    public void registerUdf(String resourcePath, String serverPath) {

        RegisterTask rt = aerospikeClient.register(null, resourcePath, serverPath, Language.LUA);
        rt.waitTillComplete();

    }

    public void registerUdf(ClassLoader cl, String resourcePath, String serverPath) {

        RegisterTask rt = aerospikeClient.register(null, cl, resourcePath, serverPath, Language.LUA);
        rt.waitTillComplete();

    }

    public void removeUdf(String serverPath) {

        InfoPolicy infoPolicy = new InfoPolicy();

        this.aerospikeClient.removeUdf(infoPolicy, serverPath);
    }

    public void save(Long pk, String set, Object data) {

        Key key = new Key(this.namespace, set, pk);

        WritePolicy writePolicy = new WritePolicy();
        writePolicy.commitLevel = CommitLevel.COMMIT_ALL;
        writePolicy.expiration = -1;
        writePolicy.recordExistsAction = RecordExistsAction.UPDATE;

        AerospikeMapper aerospikeMapper = new AerospikeMapper();

        Bin[] bins = aerospikeMapper.serialize(data);

        this.aerospikeClient.put(writePolicy, key, bins);
    }
}
