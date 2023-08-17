import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";
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
import { Auth } from "./pages/Auth";
import { TokenProvider } from "./contexts/TokenContext";
import { ProfilePage } from "./pages/ProfilePage";

function App() {
  return (
    <TokenProvider>
      <ThemeProvider>
        <AlertProvider>
          <Router>
            <Routes>
              <Route path="/" element={<LoginPage />} />
              <Route path="/login" element={<LoginPage />} />
              {/* <Route path="/login/oauth2/code/github" element={<LoginPage />} /> */}
              <Route path="/register" element={<RegisterPage />} />
              <Route path="/login/oauth2/code/github" element={<Auth />} />
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
              <Route path="/profile" element={<ProfilePage />} />
              <Route path="*" element={<Navigate to="/login" replace />} />
            </Routes>
          </Router>
        </AlertProvider>
      </ThemeProvider>
    </TokenProvider>
  );
}

export default App;
