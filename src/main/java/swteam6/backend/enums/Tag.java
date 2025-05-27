package swteam6.backend.enums;

public enum Tag{
    단체회식("단체회식하기 좋아요"),
    분위기("분위기가 좋아요"),
    가성비("가성비가 좋아요"),
    화장실굿("화장실이 깨끗해요"),
    안주맛집("안주가 맛있어요"),
    직원친절("직원들이 친절해요"),
    적합2차("2차로 좋아요"),
    대화하기좋음("대화하기 좋아요"),
    그냥그래("그냥 그래요"),
    비추천("비추천");

    private final String description;

    Tag(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

