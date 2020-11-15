package com.sugon.analysis.domain.data.subset;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @description: »º´æ
 * @author: liuwei
 * @create: 2020-03-21 13:52
 **/
@Entity(name = "customCache")
@Data
public class CustomCache {
    @Id
    private String key;
    private String value;

    public CustomCache() {
    }

    public CustomCache(String key, String value) {
        this.key = key;
        this.value = value;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomCache that = (CustomCache) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
