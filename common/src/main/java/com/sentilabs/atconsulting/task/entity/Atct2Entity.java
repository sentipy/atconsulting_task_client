package com.sentilabs.atconsulting.task.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sentipy on 07/07/14.
 */
@Entity
@Table(name = "atct_2", schema = "public", catalog = "atct")
public class Atct2Entity {
    private int column1;
    private String column2;
    private Date column3;

    @Id
    @Column(name = "column_1")
    public int getColumn1() {
        return column1;
    }

    public void setColumn1(int column1) {
        this.column1 = column1;
    }

    @Basic
    @Column(name = "column_2")
    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "column_3")
    public Date getColumn3() {
        return column3;
    }

    public void setColumn3(Date column3) {
        this.column3 = column3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Atct2Entity that = (Atct2Entity) o;

        if (column1 != that.column1) return false;
        if (column2 != null ? !column2.equals(that.column2) : that.column2 != null) return false;
        if (column3 != null ? !column3.equals(that.column3) : that.column3 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = column1;
        result = 31 * result + (column2 != null ? column2.hashCode() : 0);
        result = 31 * result + (column3 != null ? column3.hashCode() : 0);
        return result;
    }
}
