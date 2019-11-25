package ohtu.interfaces;

import java.util.List;
import java.sql.SQLException;;

public interface Dao<T, K> {
  void create(T object) throws SQLException;

  T read(K key) throws SQLException;

  T update(T object) throws SQLException;

  void delete(T object) throws SQLException;

  List<T> list() throws SQLException;
}