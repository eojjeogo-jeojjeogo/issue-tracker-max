import { Header } from "../components/Header/Header";
import { Issue } from "../components/Issue/Issue";
import { Background } from "../components/common/Background";

export function MainPage() {
  return (
    <Background>
      <Header />
      <Issue />
    </Background>
  );
}
