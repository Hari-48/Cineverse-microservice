package com.hari.tamil_movies.repo;

import com.hari.tamil_movies.entity.Movies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TamilMovieRepo extends JpaRepository<Movies,Long> {


    @Query(value = """
            SELECT COUNT(ID) FROM TAMIL_MOVIES
            """,nativeQuery = true)
    Long getCount();



    @Query(value = "SELECT * FROM TAMIL_MOVIES WHERE ACTOR = 'Vimal' ", nativeQuery = true)
    Page<Movies> getAllData(Pageable pageable);












//JPQL

//    @Query("SELECT COUNT(t.id) FROM TamilMovie t")
//    Long getCount();


//    JDBC

//    public long getMovieCount() throws SQLException {
//        String sql = "SELECT COUNT(ID) FROM TAMIL_MOVIES";
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql);
//             ResultSet rs = ps.executeQuery()) {
//            if (rs.next()) {
//                return rs.getLong(1);
//            }
//        }
//        return 0;
//    }




//4. Criteria API (CriteriaBuilder)


//    public Long getMovieCountUsingCriteria(EntityManager entityManager) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
//        Root<TamilMovie> root = cq.from(TamilMovie.class);
//        cq.select(cb.count(root));
//        return entityManager.createQuery(cq).getSingleResult();
//    }


//Named Query
//    @Entity
//    @NamedQuery(name = "TamilMovie.countAll", query = "SELECT COUNT(t.id) FROM TamilMovie t")
//    public class TamilMovie {
//        @Id
//        private Long id;
//    }

//    @Query(name = "TamilMovie.countAll")
//    Long getCount();


    // Entity manager with JPQL

//    public Long getCount(EntityManager em) {
//        return em.createQuery("SELECT COUNT(t.id) FROM TamilMovie t", Long.class)
//                .getSingleResult();
//    }



}
