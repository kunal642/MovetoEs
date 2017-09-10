/**
 * Created by kunal on 9/9/17.
 */
class Main extends App {

  new Elasticsearch(new MySQL("default", "table1")).saveToEs

}
