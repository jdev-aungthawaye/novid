package jdev.novid.common.identity;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;

import jdev.novid.component.asmapper.BinConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Embeddable
@Getter
@EqualsAndHashCode
public class UserId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    protected Long id;

    public UserId(Long value) {

        this.id = value;

    }

    @SuppressWarnings("unused")
    private UserId() {

    }

    public static class AerospikeConverter implements BinConverter {

        @Override
        public Object deserialize(String binName, Map<String, Object> source) {

            return new UserId((Long) source.get(binName));

        }

        @Override
        public Object deserialize(String binName, Record source) {

            return new UserId(source.getLong(binName));

        }

        @Override
        public Bin serialize(String binName, Object source) {

            if (source instanceof UserId) {

                return new Bin(binName, com.aerospike.client.Value.get(((UserId) source).getId()));

            }

            throw new IllegalArgumentException("Source is not LocationId.");

        }

    }

}
