import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { LoginPage } from "./pages/LoginPage";
import { IssuePage } from "./pages/IssuePage";
import { RegisterPage } from "./pages/RegisterPage";
import { LabelPage } from "./pages/LabelPage";
import { MilestonePage } from "./pages/MilestonePage";
import { ThemeProvider } from "./contexts/ThemeContext";
import { AlertProvider } from "./contexts/AlertContext";
import { NewIssuePage } from "./pages/NewIssuePage";
import { IssueDetailPage } from "./pages/IssueDetailPage";
import { IssueProvider } from "./contexts/IssueContext";

function App() {
  return (
    <ThemeProvider>
      <AlertProvider>
        <Router>
          <Routes>
            <Route path="/" element={<LoginPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route
              path="/issues/*"
              element={
                <IssueProvider>
                  <Routes>
                    <Route path="/" element={<IssuePage />} />{" "}
                    <Route path="new" element={<NewIssuePage />} />
                    <Route path=":id" element={<IssueDetailPage />} />
                  </Routes>
                </IssueProvider>
              }
            />
            <Route path="/label" element={<LabelPage />} />
            <Route path="/milestone" element={<MilestonePage />} />
          </Routes>
        </Router>
      </AlertProvider>
    </ThemeProvider>
  );
}

export default App;
