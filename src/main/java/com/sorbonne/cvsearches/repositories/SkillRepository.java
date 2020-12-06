package com.sorbonne.cvsearches.repositories;

import com.sorbonne.cvsearches.models.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends CrudRepository<Skill, Long> {
}
