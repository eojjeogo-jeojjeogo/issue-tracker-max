import { useParams } from "react-router-dom";
import { Header } from "../components/Header/Header";
import { Background } from "../components/common/Background";
import { MainArea } from "../components/common/MainArea";
import { useEffect, useState } from "react";
import { LoadingBar } from "../components/common/LoadingBar";
import { Txt } from "../components/util/Txt";
import { ColorScheme } from "../contexts/ThemeContext";
import { css, useTheme } from "@emotion/react";
import { Button } from "../components/common/Button";

import { Icon } from "../components/common/Icon";
import { Comment } from "../components/Issue/Comment";
import { TextArea } from "../components/common/TextArea";
import { AddBox } from "../components/Issue/AddBox";

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
    nickName: string;
    imgUrl: string;
  }[];
  labels: {
    id: number;
    title: string;
    description: string;
    backgroundColor: string;
    textColor: string;
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

const infoTag = (color: ColorScheme) => css`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 4px;
  width: max-content;
  height: 32px;
  padding: 0 16px;
  border-radius: 16px;
  background-color: ${color.palette.blue};
`;

const ISSUE_DETAIL_URL =
  "http://aed497a9-4c3a-45bf-91b8-433463633b2e.mock.pstmn.io/api/eojjeogojeojjeogo/issues/:issueId";

export function IssueDetailPage() {
  const [issueDetail, setIssueDetail] = useState<IssueDetail>();
  const [loading, setLoading] = useState(true);
  const { id } = useParams();
  const color = useTheme() as ColorScheme;

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);

        const issueDetailResponse = await fetch(ISSUE_DETAIL_URL);
        const issueDetailData = await issueDetailResponse.json();

        setIssueDetail(issueDetailData);

        setLoading(false);
      } catch (error) {
        console.error("API 요청 중 에러 발생:", error);
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  if (loading) {
    return (
      <Background>
        <LoadingBar />
        <Header />
      </Background>
    );
  }

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
            <div css={{ display: "flex", gap: "16px" }}>
              <div css={titleContainer}>
                <Txt typography="bold32" color={color.neutral.text.strong}>
                  {issueDetail?.title}
                </Txt>
                <Txt typography="bold32" color={color.neutral.text.weak}>
                  #{id}
                </Txt>
              </div>
              <div css={buttonContainer}>
                <Button size="S" type="outline" icon="edit" text="제목 편집" />
                <Button
                  size="S"
                  type="outline"
                  icon="archive"
                  text="이슈 닫기"
                />
              </div>
            </div>
            <div
              css={{
                display: "flex",
                gap: "8px",
                width: "100%",
                height: "32px",
              }}>
              <div css={infoTag(color)}>
                <Icon type="alertCircle" color={color.brand.text.default} />
                <Txt typography="medium12" color={color.brand.text.default}>
                  열린 이슈
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
                <Comment key={idx} comment={comment} />
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
            <AddBox mode="edit" issueDetail={issueDetail} />
          </div>
        </div>
      </MainArea>
    </Background>
  );
}
