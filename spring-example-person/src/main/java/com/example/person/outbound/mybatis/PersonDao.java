package com.example.person.outbound.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.person.service.model.Person;
import com.example.person.service.model.PersonCondition;

@Mapper
public interface PersonDao {

    @Insert("INSERT INTO"
            + " people "
            + "(id,"
            + " version,"
            + " name,"
            + " birth_date,"
            + " gender_code,"
            + " memo,"
            + " created_at,"
            + " created_by,"
            + " updated_at,"
            + " updated_by) "
            + "VALUES "
            + "(#{id},"
            + " #{version},"
            + " #{name},"
            + " #{birthDate},"
            + " #{genderCode},"
            + " #{memo},"
            + " #{createdAt},"
            + " #{createdBy},"
            + " #{updatedAt},"
            + " #{updatedBy})")
    void create(Person person);

    void update(Person person);

    void delete(Person person);

    @Select("SELECT"
            + " p.id,"
            + " p.version,"
            + " p.name,"
            + " p.birth_date,"
            + " p.gender_code,"
            + " g.gender_name"
            + " p.memo,"
            + " p.created_at,"
            + " p.created_by,"
            + " p.updated_at,"
            + " p.updated_by "
            + "FROM"
            + " people AS p "
            + "INNER JOIN"
            + " genders AS g "
            + "ON"
            + " p.gender_code = g.gender_code "
            + "WHERE"
            + " p.id = #{id}")
    Person getById(Long id);

    @Select("SELECT"
            + " p.id,"
            + " p.version,"
            + " p.name,"
            + " p.birth_date,"
            + " p.gender_code,"
            + " g.gender_name,"
            + " p.memo,"
            + " p.created_at,"
            + " p.created_by,"
            + " p.updated_at,"
            + " p.updated_by "
            + "FROM"
            + " people AS p "
            + "INNER JOIN"
            + " genders AS g "
            + "ON"
            + " p.gender_code = g.gender_code " /*
            + "<where>"
            + "<if test='idIn != null and not idIn.isEmply()'>"
            + " p.id IN "
            + "<foreach item='id' collection='idIn' open='(' separator=',' close=')'>"
            + " #{id}"
            + "</foreach>"
            + "</if>"
            + "</where>"*/)
    List<Person> find(PersonCondition condition);

}
