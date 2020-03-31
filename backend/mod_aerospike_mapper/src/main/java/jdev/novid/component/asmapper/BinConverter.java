package jdev.novid.component.asmapper;

import java.util.Map;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;

public interface BinConverter {

    public Object deserialize(String binName, Map<String, Object> source);

    public Object deserialize(String binName, Record source);

    public Bin serialize(String binName, Object source);
}
