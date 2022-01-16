import com.jing.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description: 测试文件
 * @author: GXK
 * @create: 2022-01-10 12:27
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class HttpBreakTest {



    @Test
    public void testBreak() {
        int i = 1;
        switch (i) {
            case 1:
                i++;
                System.out.println("i " + i);
            case 2:
                i++;
                System.out.println("2 " + i);
                break;
            case 3:
                i++;
                System.out.println("3 " + i);
                break;
            default:
                System.out.println("default");
                break;
        }
    }
}


