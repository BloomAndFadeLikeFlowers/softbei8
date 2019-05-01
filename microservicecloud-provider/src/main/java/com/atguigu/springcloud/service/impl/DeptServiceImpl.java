package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.common.ParamData;
import com.atguigu.springcloud.repository.DeptRepository;
import com.atguigu.springcloud.entities.Dept;
import com.atguigu.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptRepository deptRepository;

    @Override
    @Transactional
    public Dept add(Dept dept) {
        dept.setDbSource(deptRepository.getDatabaseName());
        return deptRepository.save(dept);
    }

    @Override
    public Dept get(Long deptno) {
        return deptRepository.findByDeptNo(deptno);
    }

    @Override
    public void del(Long deptno) {
        deptRepository.delete(deptno);
    }

    public Page<Dept> findAll(ParamData paramData) {
        Pageable pageable = new PageRequest(paramData.pageNumber, paramData.pageSize);
        Page<Dept> deptList = deptRepository.findAll(new Specification<Dept>() {
            @Override
            public Predicate toPredicate(Root<Dept> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                Map map = paramData.paramMap;
                if (!StringUtils.isEmpty(map.get("deptNo"))) {
                    predicate.add(cb.equal(root.get("deptNo").as(Long.class), Long.parseLong(map.get("deptNo").toString())));
                }
                if (!StringUtils.isEmpty(map.get("deptName"))) {
                    predicate.add(cb.like(root.get("deptName").as(String.class), "%" + map.get("deptName") + "%"));
                }
                if (!StringUtils.isEmpty(map.get("dbSource"))) {
                    predicate.add(cb.like(root.get("dbSource").as(String.class), "%" + map.get("dbSource") + "%"));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        }, pageable);
        return deptList;
    }
}
