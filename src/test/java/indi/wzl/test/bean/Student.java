package indi.wzl.test.bean;

import indi.wzl.annotation.ExcelCell;
import indi.wzl.annotation.HeadName;

import java.util.Date;

public class Student
{
    @ExcelCell(0)
    @HeadName("id")
    private long id;
    @ExcelCell(1)
    @HeadName("名称")
    private String name;
    @ExcelCell(2)
    @HeadName("年龄")
    private int age;
    @ExcelCell(3)
    @HeadName("性别")
    private boolean sex;
    @ExcelCell(4)
    @HeadName("生日")
    private Date birthday;

    public Student()
    {
    }

    public Student(long id, String name, int age, boolean sex, Date birthday)
    {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public boolean getSex()
    {
        return sex;
    }

    public void setSex(boolean sex)
    {
        this.sex = sex;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

}
