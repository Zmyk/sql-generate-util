package com.zmy.util.component;

/**
 * @program: sql-generate-util
 * @description: GroupBy组件
 * @author: zhangmy
 * @create: 2021-12-04 23:15
 */
public class GroupBy extends Component{

    private String table;
    private String column;

    public String getTable() {
        return table;
    }

    public String getColumn() {
        return column;
    }

    private GroupBy(String table, String column) {
        this.table = table;
        this.column = column;
    }

    @Override
    public String toString() {
        return "GroupBy{" +
                "table='" + table + '\'' +
                ", column='" + column + '\'' +
                '}';
    }

    public static class GroupByBuilder extends MemberCheck<GroupBy,GroupByBuilder> {

        private String table;
        private String column;


        public GroupByBuilder table(String table) {
            this.table = table;
            return this;
        }
        public GroupByBuilder column(String column) {
            this.column = column;
            return this;
        }

        public static GroupByBuilder builder() {
            return new GroupByBuilder();
        }

        @Override
        protected GroupBy buildInstance() {
            return new GroupBy(table,column);
        }
    }


}
