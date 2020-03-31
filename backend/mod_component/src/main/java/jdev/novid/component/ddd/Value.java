package jdev.novid.component.ddd;

import java.io.Serializable;

import lombok.Getter;

@Getter
public abstract class Value implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected String value;

    public Value(String value) {

        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {

            return false;
        }

        if (obj instanceof String) {

            return this.value.equals(obj);
        } else if (obj instanceof Value) {

            return ((Value) obj).value.equals(this.value);
        }

        return this == obj;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public String toString() {

        return this.value;
    }

}