package swteam6.backend.enums;

public enum Tag{
    CLEAN_BATHROOM("화장실이 깨끗해요"),
    GOOD_FOR_SECOND_ROUND("2차 가기 좋아요"),
    GOOD_FOR_FIRST_ROUND("1차로 좋아요"),
    GOOD_FOR_GROUP("단체모임에 좋아요");

    private final String description;

    Tag(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

