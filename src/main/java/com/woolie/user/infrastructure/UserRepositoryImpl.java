//package com.woolie.user.infrastructure;
//
//import com.woolie.user.domain.User;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public class UserRepositoryImpl implements UserRepository{
//    private final JPAQueryFactory jpaQueryFactory;
//    @Override
//    public Optional<User> findUserByEmail(String email) {
//        try {
//            return Optional.ofNullable(jpaQueryFactory.select(user)
//                    .from(user)
//                    .where(user.email.address.eq(email))
//                    .fetchOne());
//        } catch (NonUniqueResultException e) {
//            log.error("",e);
//            throw new UnableToProcessException(REQUEST_CONFLICT);
//        }
//    }
//}
