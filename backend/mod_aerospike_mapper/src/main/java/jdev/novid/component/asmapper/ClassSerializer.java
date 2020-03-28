package jdev.novid.component.asmapper;

import com.aerospike.client.Bin;

public interface ClassSerializer {

    public Bin[] serialize(Object source);
}
