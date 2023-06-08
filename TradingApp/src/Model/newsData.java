package Model;
import java.time.LocalDateTime;

public class newsData {
        private String title;
        private String description;
        private String url;
        private LocalDateTime publishedAt;

        public newsData(String title, String description, String url, LocalDateTime publishedAt) {
            this.title = title;
            this.description = description;
            this.url = url;
            this.publishedAt = publishedAt;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getUrl() {
            return url;
        }

        public LocalDateTime getPublishedAt() {
            return publishedAt;
        }
    }


