function call_ajax() {
    $("tbody").empty()
    var target = $("input[type=date]").val().replace(/-/gi, "")
    call_API()
    $.ajax({
        async: true, // ajax sync를 동기 방식으로 할지, 비동기 방식으로 할지  비동기 방식의 호출은 true!

        url: "https://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=0f41e4c4a0fa72f8f005a0f48d5f797e&targetDt=" + target,

        type: "GET",
        timeout: 3000,
        dataType: "json",  // 결과 JSON을 JavaScript객체로 변환
        success: function (result) {   // 서버가 주는 데이터 : result
            // alert("호출성공!")      // 제목
            $.each(result.boxOfficeResult.dailyBoxOfficeList, function (idx, item) {  // for문과 동일한 방식이다. 각각의 result에 대해 function을 수행
                var tr = $("<tr></tr>")
                var rank = $("<td></td>").text(item.rank)

                var movieNm = $("<td></td>").text(item.movieNm)

                var image_url = call_API(item.movieNm)

                var imgTd = $("<td></td>")
                var img = $("<img \>").attr("src", image_url).css("width", "150px")
                imgTd.append(img)

                var salesAcc = $("<td></td>").text(item.salesAcc)
                var audiAcc = $("<td></td>").text(item.audiAcc)


                var infoBtn = $("<input \>").attr({type: "button", value: "상세정보"})

                infoBtn.on("click", function () {
                    // 현재 클릭된 버튼에 대한 책 정보를 찾아서 삭제.
                    // this : 현재 이벤트가 발생된 객체를 지칭.
                    var movie_target = item.movieCd
                    get_more_info(movie_target)
                }) // "(click)"할때 이벤트 처리(함수)


                tr.append(rank)
                tr.append(imgTd)
                tr.append(movieNm)
                tr.append(salesAcc)
                tr.append(audiAcc)
                tr.append(infoBtn)

                $("tbody").append(tr)
            })

        },
        error: function (error) {
            alert("서버호출 실패")
        }
    })
}


function get_more_info(movie_target) {

    $.ajax({
        async: true, // ajax sync를 동기 방식으로 할지, 비동기 방식으로 할지  비동기 방식의 호출은 true!
        url: "https://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=0f41e4c4a0fa72f8f005a0f48d5f797e&movieCd=" + movie_target,
        type: "GET",
        timeout: 3000,
        dataType: "json",  // 결과 JSON을 JavaScript객체로 변환
        success: function (result) {   // 서버가 주는 데이터 : result
            var movieNm = result.movieInfoResult.movieInfo["movieNm"]
            var openDt = result.movieInfoResult.movieInfo["openDt"]
            var genres = ""
            for (var i in result.movieInfoResult.movieInfo.genres) {
                genres += result.movieInfoResult.movieInfo.genres[i].genreNm + ", "
            }
            genres = genres.slice(0, -2)
            var produceNm = result.movieInfoResult.movieInfo.directors[0]["peopleNm"]
            var actors = ""
            for (var i in result.movieInfoResult.movieInfo.actors) {
                actors += result.movieInfoResult.movieInfo.actors[i].peopleNm + ", "
            }
            actors = actors.slice(0, -2)

            alerts = "영화 이름 : " + movieNm + "\n" +
                "개봉 날짜 : " + openDt + "\n" +
                "영화 장르 : " + genres + "\n" +
                "감독 이름 : " + produceNm + "\n" +
                "배우들 : " + actors

            alert(alerts)
        },
        error: function (error) {
            alert("영화정보 호출 실패")
        }
    })
}

function call_API(movieNm) {
    var img_url
    $.ajax({
        async: false,
        url: "https://dapi.kakao.com/v2/search/image",
        type: "GET",
        timeout: 3000,
        data: {query: movieNm + " 영화포스터", size: 30},
        dataType: "json",
        headers: {Authorization: "KakaoAK a810727b39e6ad7f1b151bab4e2c3de2"},
        // beforeSend : function(xhr){
        //     xhr.setRequestHeader("Authoriztion","KakaoAK a810727b39e6ad7f1b151bab4e2c3de2")
        // },
        success: function (result) {
            img_url = result.documents[0].image_url
        },
        error: function () {
            img_url = "image/no_img.jpg"
        }

    })
    return img_url
}