import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import { useAuthStore } from '@/store/authStore'
import LandingPage from '@/pages/LandingPage'
import LoginPage from '@/pages/LoginPage'
import RegisterPage from '@/pages/RegisterPage'
import DashboardPage from '@/pages/DashboardPage'
import AccountsPage from '@/pages/AccountsPage'
import AddAccountPage from '@/pages/AddAccountPage'
import TransactionsPage from '@/pages/TransactionsPage'
import { Sidebar } from '@/components/layout/Sidebar'
import { Header } from '@/components/layout/Header'

function ProtectedLayout({ children }: { children: React.ReactNode }) {
  const { isAuthenticated } = useAuthStore()
  if (!isAuthenticated) return <Navigate to="/login" replace />

  return (
    <div className="flex h-screen bg-gray-50">
      <Sidebar />
      <div className="flex flex-col flex-1 overflow-hidden">
        <Header />
        <main className="flex-1 overflow-auto p-6">
          {children}
        </main>
      </div>
    </div>
  )
}

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/dashboard" element={
          <ProtectedLayout><DashboardPage /></ProtectedLayout>
        } />
        <Route path="/accounts" element={
          <ProtectedLayout><AccountsPage /></ProtectedLayout>
        } />
        <Route path="/accounts/add" element={
          <ProtectedLayout><AddAccountPage /></ProtectedLayout>
        } />
        <Route path="/transactions" element={
          <ProtectedLayout><TransactionsPage /></ProtectedLayout>
        } />
      </Routes>
    </BrowserRouter>
  )
}

export default App
