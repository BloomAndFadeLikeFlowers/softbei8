package com.atguigu.springcloud.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "dept")
public class Dept implements Serializable// entity --orm--- db_table
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long deptNo; // 主键
    private String deptName; // 部门名称
    @Column(name="dbSource",columnDefinition="DATABASE()")
    private String dbSource;// 来自那个数据库，因为微服务架构可以一个服务对应一个数据库，同一个信息被存储到不同数据库
}
