package com.mts.test;

/**
 * Created by r457r on 04.12.2016.
 * Class representing db record
 */
public final class Record {
    public final Integer id;
    public final String name1;
    public final String name2;

    Record(Integer id, String name1, String name2){
        this.id = id;
        this.name1 = name1;
        this.name2 = name2;
    }
}
