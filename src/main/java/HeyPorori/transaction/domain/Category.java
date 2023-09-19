package HeyPorori.transaction.domain;

import HeyPorori.transaction.config.BaseException;
import HeyPorori.transaction.config.BaseResponseStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Stream;

@Getter
public enum Category {
    ELECTRONICS("전자제품", "스마트폰, 컴퓨터, 태블릿, TV, 오디오 기기 등"),
    CLOTHING_ACCESSORIES("의류 및 액세서리", "옷, 신발, 가방, 모자, 보석 등"),
    FURNITURE_HOME_GOODS("가구 및 가정용품", "테이블, 의자, 침대, 조명, 주방 용품 등"),
    SPORTS_LEISURE("스포츠 및 레저", "운동기구, 캠핑 용품, 자전거, 낚시 용품 등"),
    CARS_MOTORCYCLES("자동차 및 오토바이", "중고차, 오토바이, 자동차 부품, 액세서리 등"),
    BOOKS_MUSIC("도서 및 음악", "소설, 교육자료, CD, LP, 악기 등"),
    BABY_KIDS("아기 및 어린이 용품", "아기 의류, 장난감, 유모차, 아기 침대 등"),
    ETC("기타", "");

    private final String name;
    private final String description;

    Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static Category parsing(String inputValue) {
        return Arrays.stream(Category.values())
                .filter(r -> r.name.equals(inputValue))
                .findAny().orElse(null);
    }
}