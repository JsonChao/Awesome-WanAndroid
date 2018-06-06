package json.chao.com.wanandroid.JunitAndMockitoTestExample;

/**
 * @author quchao
 * @date 2018/6/4
 */
public class Home {

    private Person mPerson;

    public Home(Person person) {
        mPerson = person;
    }

    public int getAge(){
        return mPerson.getAge();
    }
}