package sample;

    public class User {


        private String login;
        private Integer score;
        private Integer linesCleared;

        public User(String login, Integer score, Integer linesCleared) {
            this.login = login;
            this.score = score;
            this.linesCleared = linesCleared;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public void setLinesCleared(Integer linesCleared) {
            this.linesCleared = linesCleared;
        }

        public String getLogin() {
            return login;
        }

        public int getScore() {
            return score;
        }

        public int getLinesCleared() {
            return linesCleared;
        }
}
