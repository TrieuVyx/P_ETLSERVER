package com.wilmar.p_server.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import com.wilmar.p_server.entity.token.RefreshToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.hibernate.mapping.Collection;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String reFreshToken);
//    @Query(value = "SELECT U FROM UserEntity U, RefreshToken R WHERE U.id  = R.userId"  )
//    RefreshToken delete(@Param("id")  Integer id);
}
