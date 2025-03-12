    package com.example.Twitter.repository;

    import com.example.Twitter.entity.Authentication;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.Optional;

    public interface AuthenticationRepository extends JpaRepository<Authentication,Long> {

    Optional<Authentication>findByUser_Id(long id);
    }
