namespace java org.example.web.thrift.userpurse.api

exception InvalidOperationException {
    1: i32 code,
    2: string description
}

struct UserPurseBo {
    1: i64 uid,
    2: i64 goldNum,
    3: i32 state,
}

service IUserPurseService {

    UserPurseBo getUserPurseInfo(1:i64 id) throws (1:InvalidOperationException e),
    UserPurseBo getUserPurseInfoCache(1:i64 id) throws (1:InvalidOperationException e),
    i64 incrUserPurseGoldNum(1:i64 id,2:i64 num) throws (1:InvalidOperationException e),

    UserPurseBo getUserPurseInfoLocal(1:i64 id) throws (1:InvalidOperationException e)
}