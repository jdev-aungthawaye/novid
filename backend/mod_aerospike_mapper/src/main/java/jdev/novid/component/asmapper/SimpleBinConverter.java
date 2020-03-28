package jdev.novid.component.asmapper;

import java.util.Map;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;
import com.aerospike.client.Value;

public class SimpleBinConverter implements BinConverter {

    @Override
    public Object deserialize(String binName, Map<String, Object> source) {

        return source.get(binName);
    }

    @Override
    public Object deserialize(String binName, Record source) {

        return source.getValue(binName);
    }

    @Override
    public Bin serialize(String binName, Object source) {

        return new Bin(binName, Value.get(source));
    }

}
