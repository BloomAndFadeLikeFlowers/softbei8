package com.atguigu.springcloud.repository;

import com.atguigu.springcloud.entities.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptRepository extends JpaRepository<Dept, Long>, JpaSpecificationExecutor<Dept> {

    public Dept findByDeptNo(Long deptNo);

    @Modifying
    @Query(value = "INSERT INTO dept(deptName,dbSource) VALUES(?1,DATABASE())", nativeQuery = true)
    public int insert(String deptName);

    public List<Dept> findAll();

    @Query(value = "select database()", nativeQuery = true)
    public String getDatabaseName();
}
