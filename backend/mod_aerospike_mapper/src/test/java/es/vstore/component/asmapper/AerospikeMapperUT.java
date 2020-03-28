package es.vstore.component.asmapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Host;
import com.aerospike.client.Value;
import com.aerospike.client.lua.LuaConfig;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.query.IndexType;
import com.github.filosganga.geogson.model.Point;

import es.vstore.component.asmapper.sample.Gender;
import es.vstore.component.asmapper.sample.Person;
import jdev.novid.component.asmapper.AerospikeMapper;
import jdev.novid.component.asmapper.AerospikeTemplate;
import jdev.novid.component.asmapper.GeoGsonUtil;

public class AerospikeMapperUT {

    @Test
    public void test() {

        List<Host> hostList = new ArrayList<>();
        Host host = new Host("172.28.128.3", 3000);
        hostList.add(host);

        ClientPolicy policy = new ClientPolicy();
        policy.threadPool = Executors.newFixedThreadPool(5);

        AerospikeClient aerospikeClient = new AerospikeClient(policy, hostList.toArray(new Host[hostList.size()]));

        AerospikeTemplate aerospikeTemplate = new AerospikeTemplate(aerospikeClient, "platform");

        aerospikeTemplate.removeUdf("udf/vap_person.lua");

        ClassLoader cl = ClassLoader.getSystemClassLoader();

        String sourceDir = cl.getResource("udf").getPath();

        System.out.println("sourceDir : " + sourceDir);

        LuaConfig.SourceDirectory = sourceDir;

        String luaPath = cl.getResource("udf/vap_person.lua").getPath();

        System.out.println("luaPath : " + luaPath);

        aerospikeTemplate.registerUdf("udf/vap_person.lua", "vap_person.lua");

        Long personId = 1L;

        aerospikeTemplate.save(personId, "person", new Person(personId, "David", Gender.MALE, Calendar.getInstance(),
                Point.from(1, 1), "@david", "password", new BigDecimal(1000.00)));

        Optional<Person> optional = aerospikeTemplate.find(personId, "person", Person.class);

        if (optional.isPresent()) {

            Person person = optional.get();

            System.out.println("name : " + person.getName());
            System.out.println("gender : " + person.getGender());
            System.out.println("dob : " + person.getDob().getTime().toString());
            System.out.println("location : " + GeoGsonUtil.fromPoint(person.getLocation()));
            System.out.println("networth : " + person.getNetWorth().toPlainString());
        }

        aerospikeTemplate.createIndex("idx_username", "person", "username", IndexType.STRING);

        List<Person> persons = aerospikeTemplate.query("person", null, Person.class);

        System.out.println("\nFrom filter:");

        persons.forEach((person) -> {

            System.out.println("pk : " + person.getPersonId());
            System.out.println("name : " + person.getName());
            // System.out.println("gender : " + person.getGender());
            // System.out.println("dob : " +
            // person.getDob().getTime().toString());
            // System.out.println("location : " +
            // GeoGsonUtil.fromPoint(person.getLocation()));
        });

        persons = aerospikeTemplate.queryAggregate("person", AerospikeMapper.class.getClassLoader(), null, null,
                Person.class, "vap_person", "find_by_username_and_password", Value.get("@david"),
                Value.get("password1"));

        System.out.println("\nFrom UDF:");
        persons.forEach((person) -> {

            System.out.println("name : " + person.getName());
            System.out.println("gender : " + person.getGender());
            System.out.println("dob : " + person.getDob().getTime().toString());
            System.out.println("location : " + GeoGsonUtil.fromPoint(person.getLocation()));
        });

        aerospikeClient.close();
    }

}
