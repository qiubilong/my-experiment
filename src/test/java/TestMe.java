import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.example.web.thrift.userpurse.api.IUserPurseService;
import org.example.web.thrift.userpurse.api.UserPurseBo;

public class TestMe {

    public static void main(String[] args) {
        基础权重(300,100,500);
        基础权重(20,100,454);
        时间衰减();
    }

    public static void 时间衰减(){
        for (int i = 0; i < 525600;) {

            double y  = Math.exp((-i/72000D));
            i+= 60;
            System.out.println(i+"->" + minuteToDay(i) +"天->"+ y);
        }
    }

    public static int minuteToDay(int min){
        return min/24/60/60 ;
    }

    public static void 基础权重(int likeNum, int commentNum,int uv){
        double w1 = 0.1 * likeNum;
        double w2 = 0.6 * commentNum;
        double w3 = 0.02 * uv;
        double w = w1 + w2 + w3;
        System.out.println(w);
    }
}
