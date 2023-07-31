package hu.webuni.spring.jupiterwebuni.repository;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import hu.webuni.spring.jupiterwebuni.model.course.Course;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import jakarta.persistence.EntityManager;
import java.util.List;

public class QuerydslWithEntityGraphRepositoryImpl
        extends SimpleJpaRepository<Course, Long>
        implements QuerydslWithEntityGraphRepository<Course, Long> {

    private final EntityManager entityManager;
    private final EntityPath<Course> path;
    private final PathBuilder<Course> builder;
    private final Querydsl querydsl;


    public QuerydslWithEntityGraphRepositoryImpl(EntityManager em) {
        super(Course.class, em);
        this.entityManager = em;    // private final-be menti el az entitymanagert, definiálunk saját em tagváltozt
        this.path = SimpleEntityPathResolver.INSTANCE.createPath(Course.class);
        this.builder = new PathBuilder<>(path.getType(), path.getMetadata());
        this.querydsl = new Querydsl(em, builder);
    }


    @Override
    public List<Course> findAll(Predicate predicate, String entityGraphName, Sort sort) {
        JPAQuery query =
                (JPAQuery) querydsl.applySorting(sort, createQuery(predicate).select(path));    // rendezéssel
        query.setHint(EntityGraph.EntityGraphType.LOAD.getKey(), entityManager.getEntityGraph(entityGraphName));
        return query.fetch();
    }

    private JPAQuery createQuery(Predicate predicate) {
        return querydsl.createQuery(path).where(predicate);
    }
}
