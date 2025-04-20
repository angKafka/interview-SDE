package util;

import java.util.List;

public interface LocalDBOperations<T>{
    List<T> loadDataFromLocalDB();
    void saveDataToLocalDB();
}
