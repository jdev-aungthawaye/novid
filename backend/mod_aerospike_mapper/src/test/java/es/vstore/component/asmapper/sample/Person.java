package es.vstore.component.asmapper.sample;

import java.math.BigDecimal;
import java.util.Calendar;

import com.github.filosganga.geogson.model.Point;

import jdev.novid.component.asmapper.annotation.AerospikeBin;
import jdev.novid.component.asmapper.annotation.AerospikeRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AerospikeRecord
@Getter
@AllArgsConstructor
public class Person {

    @AerospikeBin(name = "p_id")
    private Long personId;

    @AerospikeBin(name = "name")
    private String name;

    @AerospikeBin(name = "gender")
    private Gender gender;

    @AerospikeBin(name = "dob")
    private Calendar dob;

    @AerospikeBin(name = "location")
    private Point location;

    @AerospikeBin(name = "username")
    private String username;

    @AerospikeBin(name = "pwd")
    private String password;

    @AerospikeBin(name = "net_worth")
    private BigDecimal netWorth;

    protected Person() {

    }
}