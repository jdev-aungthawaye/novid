package jdev.novid.component.ddd;

import lombok.Getter;

@Getter
public class DomainException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DomainException() {

        super();
    }

    public DomainException(Throwable cause) {

        super(cause);
    }

    @Override
    public String toString() {

        String exceptionName = this.getCause() == null ? this.getClass().getSimpleName()
                : this.getCause().getClass().getSimpleName();

        return exceptionName.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
                "(?<=[A-Za-z])(?=[^A-Za-z])"), " ").replaceAll(" ", "_").toUpperCase();
    }
}
