package ohtu.interfaces;

import java.util.List;
import java.sql.SQLException;

public interface Dao<T, K> {
  void create(T object) throws SQLException;

  T read(K key) throws SQLException;

  void update(T object, T secondObject) throws SQLException;

  void delete(T object) throws SQLException;

  List<T> list() throws SQLException;
}