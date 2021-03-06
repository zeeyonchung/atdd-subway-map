package nextstep.subway.line.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.subway.line.acceptance.step.LineAcceptanceStep.*;

@DisplayName("지하철 노선 관련 기능")
public class LineAcceptanceTest extends AcceptanceTest {

    @DisplayName("지하철 노선을 생성한다.")
    @Test
    void createLine() {
        // when
        ExtractableResponse<Response> response = 노선_생성_요청("신분당선");

        // then
        노선_생성됨(response);
    }

    @DisplayName("기존에 존재하는 지하철 노선 이름으로 지하철 노선을 생성한다.")
    @Test
    void createLine2() {
        // given
        노선_등록되어_있음("name1");

        // when
        ExtractableResponse<Response> response = 노선_생성_요청("name1");

        // then
        노선_생성_실패됨(response);
    }

    @DisplayName("지하철 노선 목록을 조회한다.")
    @Test
    void getLines() {
        // given
        노선_등록되어_있음("name1");
        노선_등록되어_있음("name2");

        // when
        ExtractableResponse<Response> response = 노선_목록_조회_요청();

        // then
        노선_목록_응답됨(response);
        노선_목록_포함됨(response, 2);
    }

    @DisplayName("지하철 노선을 조회한다.")
    @Test
    void getLine() {
        // given
        ExtractableResponse<Response> createResponse = 노선_등록되어_있음("신분당선");

        // when
        String uri = createResponse.header("Location");
        ExtractableResponse<Response> response = 노선_조회_요청(uri);

        // then
        노선_응답됨(response);
    }

    @DisplayName("지하철 노선을 수정한다.")
    @Test
    void updateLine() {
        // given
        ExtractableResponse<Response> createResponse = 노선_등록되어_있음("name1");

        // when
        String uri = createResponse.header("Location");
        ExtractableResponse<Response> response = 노선_수정_요청(uri);

        // then
        노선_수정됨(response);
    }

    @DisplayName("지하철 노선을 제거한다.")
    @Test
    void deleteLine() {
        // given
        ExtractableResponse<Response> createResponse = 노선_등록되어_있음("name1");

        // when
        String uri = createResponse.header("Location");
        ExtractableResponse<Response> response = 노선_제거_요청(uri);

        // then
        노선_삭제됨(response);
    }
}
