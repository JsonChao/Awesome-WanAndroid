package json.chao.com.wanandroid.test;

public class User{
    
    public long id;
    public String name;
    public String blog;


    @Override
    public String toString() {
        return "id:" + id + " name:" + name + " blog:" + blog;
    }
}