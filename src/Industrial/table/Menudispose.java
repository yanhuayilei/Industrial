package Industrial.table;

import Industrial.Block.SuperBuild;

public class Menudispose<T> {
    public final T content;
    public Menudispose(T content){
        this.content = content;
    }
    public Class getCla(){
        if (content!=null){
            return content.getClass();
        }
        return null;
    }

}
