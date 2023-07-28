import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { LoginPage } from "./pages/LoginPage";
import { MainPage } from "./pages/MainPage";
import { RegisterPage } from "./pages/RegisterPage";
import { LabelPage } from "./pages/LabelPage";
import { MilestonePage } from "./pages/MilestonePage";
import { ThemeProvider } from "./contexts/ThemeContext";
import { LabelProvider } from "./contexts/LabelContext";

function App() {
  return (
    <ThemeProvider>
      <LabelProvider>
        <Router>
          <Routes>
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/" element={<MainPage />} />
            <Route path="/label" element={<LabelPage />} />
            <Route path="/milestone" element={<MilestonePage />} />
          </Routes>
        </Router>
      </LabelProvider>
    </ThemeProvider>
  );
}

export default App;
