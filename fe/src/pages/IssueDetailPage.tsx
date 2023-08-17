import { useNavigate, useParams } from "react-router-dom";
import { Header } from "../components/Header/Header";
import { Background } from "../components/common/Background";
import { MainArea } from "../components/common/MainArea";
import { useContext, useEffect, useState } from "react";
import { LoadingBar } from "../components/common/LoadingBar";
import { Txt } from "../components/util/Txt";
import { ColorScheme } from "../contexts/ThemeContext";
import { css, useTheme } from "@emotion/react";
import { Button } from "../components/common/Button";

import { Icon } from "../components/common/Icon";
import { Comment } from "../components/Issue/Comment";
import { TextArea } from "../components/common/TextArea";
import { AddBox } from "../components/Issue/AddBox";
import { ISSUE_URL, SERVER } from "../constants/url";
import { TokenContext } from "../contexts/TokenContext";
import { AlertContext } from "../contexts/AlertContext";
import { TextInput } from "../components/common/TextInput";
import { IssueContext } from "../contexts/IssueContext";
// import { ISSUE_URL, SERVER } from "../constants/url";

export type CommentType = IssueDetail["comments"][0];

export type IssueDetail = {
  title: string;
  number: string;
  isClosed: boolean;
  createdTime: string;
  author: string;
  comments: {
    content: string;
    author: string;
    authorImg: string;
    isIssueAuthor: boolean;
    createdTime: string;
    emoticons: {
      id: number;
    }[];
    files: string;
  }[];
  assignees: {
    id: number;
    name: string;
    imgUrl: string;
  }[];
  labels: {
    id: number;
    title: string;
    description: string;
    backgroundColor: string;
    isDark: boolean;
  }[];
  milestone: {
    id: number;
    issueClosedCount: number;
    issueOpenedCount: number;
  };
  emoticons: {
    id: number;
    emoticon: string;
  }[];
};

const titleContainer = css`
  display: flex;
  gap: 8px;
  width: 992px;
  height: 48px;
`;

const buttonContainer = css`
  display: flex;
  gap: 16px;
  align-items: center;
  justify-content: flex-end;
`;

const infoTag = (color: ColorScheme, isClosed: boolean) => css`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 4px;
  width: max-content;
  height: 32px;
  padding: 0 16px;
  border-radius: 16px;
  background-color: ${isClosed ? color.neutral.text.weak : color.palette.blue};
`;

// const mockURL =
//   "https://aed497a9-4c3a-45bf-91b8-433463633b2e.mock.pstmn.io/api/eojjeogojeojjeogo/issues";

export function IssueDetailPage() {
  const [loading, setLoading] = useState(true);
  const [isTitleEditMode, setIsTitleEditMode] = useState(false);
  const [title, setTitle] = useState<string>("");
  // const [shouldFetchAgain, setShouldFetchAgain] = useState(false);

  const { id } = useParams();
  const color = useTheme() as ColorScheme;

  const tokenContextValue = useContext(TokenContext)!;
  const { accessToken } = tokenContextValue;

  const AlertContextValue = useContext(AlertContext)!;
  const {
    setIsLabelAlertOpen,
    setDeleteElementId,
    // setEditElementId,
    setCurrentType,
  } = AlertContextValue!;

  const IssueContextValue = useContext(IssueContext)!;
  const { shouldFetchAgain, setShouldFetchAgain } = IssueContextValue;

  const navigate = useNavigate();
  if (!accessToken) navigate("/login");

  const [issueDetail, setIssueDetail] = useState<IssueDetail>();

  const isTitleEditValid = title.length > 0 && title !== issueDetail?.title;

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);

        const headers = {
          "Authorization": "Bearer " + accessToken,
          "Content-Type": "application/json",
        };
        // const issueDetailResponse = await fetch(`${mockURL}/${id}`);
        const issueDetailResponse = await fetch(`${SERVER}${ISSUE_URL}/${id}`, {
          headers,
        });
        const issueDetailData = await issueDetailResponse.json();

        console.log(issueDetailData);
        setIssueDetail(issueDetailData);
        setTitle(issueDetailData.title);

        setLoading(false);
      } catch (error) {
        console.error("API 요청 중 에러 발생:", error);
        setLoading(false);
      }
    };

    fetchData();
    setShouldFetchAgain(false);
  }, [shouldFetchAgain]);

  const onClickTitleEditButton = () => {
    setIsTitleEditMode(true);
  };

  const onClickTitleEditCancelButton = () => {
    setIsTitleEditMode(false);
  };

  const onChangeInputTitle = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };

  if (loading) {
    return (
      <Background>
        <LoadingBar />
        <Header />
      </Background>
    );
  }

  const onClickDeleteButton = async () => {
    setIsLabelAlertOpen(true);
    setCurrentType("issue");
    setDeleteElementId(Number(id));
  };

  const onClickTitleEditCompleteButton = async () => {
    try {
      const response = await fetch(`${SERVER}${ISSUE_URL}/${id}/title`, {
        method: "PATCH",
        headers: {
          "Authorization": "Bearer " + accessToken,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          title,
        }),
      });

      if (response.ok) {
        setIsTitleEditMode(false);
        setShouldFetchAgain(true);
      }
    } catch (error) {
      console.error(error);
    }
  };

  const onClickCloseButton = async () => {
    try {
      const response = await fetch(`${SERVER}${ISSUE_URL}/${id}/status`, {
        method: "PATCH",
        headers: {
          "Authorization": "Bearer " + accessToken,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          isClosed: issueDetail?.isClosed ? false : true,
        }),
      });

      if (response.ok) {
        navigate("/issues");
      }
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Background>
      <Header />
      <MainArea>
        <div css={{ display: "flex", flexDirection: "column", gap: "24px" }}>
          <div
            css={{
              display: "flex",
              width: "",
              flexDirection: "column",
              gap: "16px",
            }}>
            {isTitleEditMode ? (
              <div css={{ display: "flex", gap: "16px" }}>
                <TextInput
                  inputValue={title}
                  onChange={onChangeInputTitle}
                  placeholder="제목"
                  width="992px"
                  height={40}
                />
                <div css={buttonContainer}>
                  <Button
                    onClick={onClickTitleEditCancelButton}
                    size="S"
                    type="outline"
                    icon="xSquare"
                    text="편집 취소"
                  />
                  <Button
                    onClick={onClickTitleEditCompleteButton}
                    size="S"
                    status={isTitleEditValid ? "enabled" : "disabled"}
                    type="contained"
                    icon="edit"
                    text="편집 완료"
                  />
                </div>
              </div>
            ) : (
              <div css={{ display: "flex", gap: "16px" }}>
                <div css={titleContainer}>
                  <Txt typography="bold32" color={color.neutral.text.strong}>
                    {issueDetail?.title}
                  </Txt>
                  <Txt typography="bold32" color={color.neutral.text.weak}>
                    #{issueDetail?.number}
                  </Txt>
                </div>
                <div css={buttonContainer}>
                  <Button
                    onClick={onClickTitleEditButton}
                    size="S"
                    type="outline"
                    icon="edit"
                    text="제목 편집"
                  />
                  <Button
                    onClick={onClickCloseButton}
                    size="S"
                    type="outline"
                    icon="archive"
                    text={issueDetail?.isClosed ? "이슈 열기" : "이슈 닫기"}
                  />
                </div>
              </div>
            )}
            <div
              css={{
                display: "flex",
                gap: "8px",
                width: "100%",
                height: "32px",
              }}>
              <div css={infoTag(color, issueDetail!.isClosed)}>
                <Icon type="alertCircle" color={color.brand.text.default} />
                <Txt typography="medium12" color={color.brand.text.default}>
                  {issueDetail?.isClosed ? "닫힌 이슈" : "열린 이슈"}
                </Txt>
              </div>
              <Txt typography="medium16" color={color.neutral.text.weak}>
                이 이슈가 3분 전에 {issueDetail?.author}님에 의해 열렸습니다
              </Txt>
              <Txt typography="medium16" color={color.neutral.text.weak}>
                ∙
              </Txt>
              <Txt typography="medium16" color={color.neutral.text.weak}>
                코멘트 {issueDetail?.comments.length}개
              </Txt>
            </div>
          </div>
          <div
            css={{
              width: "100%",
              height: "1px",
              borderTop: `1px solid ${color.neutral.border.default}`,
            }}></div>
          <div
            css={{
              display: "flex",
              justifyContent: "space-between",
              gap: "32px",
              width: "100%",
            }}>
            <div
              css={{
                display: "flex",
                flexDirection: "column",
                gap: "24px",
                alignItems: "space-between",
              }}>
              {issueDetail?.comments.map((comment, idx) => (
                <Comment
                  authorId={issueDetail.author}
                  key={idx}
                  comment={comment}
                />
              ))}
              <TextArea
                height="184px"
                areaHeight="64px"
                areaMaxHeight="110px"
              />
              <div css={{ display: "flex", justifyContent: "flex-end" }}>
                <Button
                  size="S"
                  type="contained"
                  status="disabled"
                  icon="plus"
                  text="코멘트 작성"
                />
              </div>
            </div>
            <div
              css={{
                display: "flex",
                flexDirection: "column",
                gap: "16px",
                alignItems: "flex-end",
              }}>
              <AddBox issueId={id!} issueDetail={issueDetail} mode="edit" />
              <div
                css={{
                  display: "flex",

                  width: "81px",
                  height: "32px",
                  // paddingRight: "16px",
                }}>
                <Button
                  onClick={onClickDeleteButton}
                  textColor={color.danger.text.default}
                  icon="trash"
                  type="ghost"
                  size="S"
                  text="이슈 삭제"
                />
              </div>
            </div>
          </div>
        </div>
      </MainArea>
    </Background>
  );
}

// const issueDetail = {
//   id: 1,
//   number: 1,
//   title: "이슈 제목",
//   isClosed: true,
//   createdTime: "2023-07-30T10:00:0000",
//   author: "박하",
//   comments: [
//     {
//       id: 1,
//       content: "comment 내용",
//       author: "박하",
//       authorImg:
//         "https://i.namu.wiki/i/abZPxKt_L98I8ttqw56pLHtGiR5pAV4YYmpR3Ny3_n0yvff5IDoKEQFof7EbzJUSZ_-uzR5S7tzTzGQ346Qixw.webp",
//       isIssueAuthor: true,
//       createdTime: "2023-07-30T10:00:0000",
//       emoticons: [
//         {
//           id: 1,
//           emoticon: "U+1F600",
//           memberNickname: ["charlie", "박하", "지니", "앨버트"],
//         },
//         {
//           id: 2,
//           emoticon: "U+1F603",
//           memberNickname: ["charlie", "박하"],
//         },
//       ],
//       files: "fileUrl",
//     },
//     {
//       id: 2,
//       content: "comment 내용2",
//       author: "지니",
//       authorImg:
//         "https://src.hidoc.co.kr/image/lib/2022/5/12/1652337370806_0.jpg",
//       isIssueAuthor: true,
//       createdTime: "2023-07-30T10:00:0000",
//       emoticons: [
//         {
//           id: 3,
//           emoticon: "U+1F604",
//           memberNickname: ["charlie", "박하", "지니", "앨버트"],
//         },
//         {
//           id: 2,
//           emoticon: "U+1F603",
//           memberNickname: ["charlie", "박하", "지니"],
//         },
//       ],
//       files: "fileUrl",
//     },
//     {
//       id: 3,
//       content: "comment 내용3",
//       author: "박하",
//       authorImg:
//         "https://i.namu.wiki/i/abZPxKt_L98I8ttqw56pLHtGiR5pAV4YYmpR3Ny3_n0yvff5IDoKEQFof7EbzJUSZ_-uzR5S7tzTzGQ346Qixw.webp",
//       isIssueAuthor: true,
//       createdTime: "2023-07-30T10:00:0000",
//       emoticons: [
//         {
//           id: 3,
//           emoticon: "U+1F604",
//           memberNickname: ["charlie", "박하", "지니", "앨버트"],
//         },
//         {
//           id: 2,
//           emoticon: "U+1F603",
//           memberNickname: ["charlie", "박하", "지니"],
//         },
//       ],
//       files: "fileUrl",
//     },
//   ],
//   assignees: [
//     {
//       id: 1,
//       nickName: "찰리",
//       imgUrl: "imgUrl",
//     },
//     {
//       id: 2,
//       nickName: "지니",
//       imgUrl: "imgUrl",
//     },
//   ],
//   labels: [
//     {
//       id: 1,
//       title: "테이블 이름",
//       description: "레이블 설명",
//       backgroundColor: "#004DE3",
//       isDark: true,
//     },
//     {
//       id: 2,
//       title: "테이블 이름",
//       description: "레이블 설명",
//       backgroundColor: "#004DE3",
//       isDark: true,
//     },
//   ],
//   milestone: {
//     id: 1,
//     title: "이번주 할일",
//     issueClosedCount: 2,
//     issueOpenedCount: 4,
//   },
//   emoticons: [
//     {
//       id: 1,
//       emoticon: "U+1F600",
//     },
//     {
//       id: 2,
//       emoticon: "U+1F603",
//     },
//     {
//       id: 3,
//       emoticon: "U+1F604",
//     },
//     {
//       id: 4,
//       emoticon: "U+1F601",
//     },
//     {
//       id: 5,
//       emoticon: "U+1F606",
//     },
//   ],
// };
