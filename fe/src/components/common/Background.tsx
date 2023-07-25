import { color } from "../../constants/colors";

export function Background({ children }: { children: React.ReactNode }) {
  return (
    <>
      <div
        css={{
          display: "flex",
          flexDirection: "column",

          margin: "0 auto",
          position: "relative",

          alignItems: "center",
          width: "1440px",
          height: "1024px",
          backgroundColor: color.neutral.surface.default,
        }}>
        {children}
      </div>
    </>
  );
}
