import { useNavigate } from 'react-router-dom'
import { LogOut, User } from 'lucide-react'
import { useAuthStore } from '@/store/authStore'

export function Header() {
  const { user, logout } = useAuthStore()
  const navigate = useNavigate()

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  return (
    <header className="h-16 bg-white border-b border-gray-200 flex items-center justify-between px-6">
      <div className="flex items-center gap-2">
        <span className="text-sm text-gray-500">Bénin 🇧🇯</span>
        <span className="text-gray-300">·</span>
        <span className="text-sm text-gray-500">FCFA</span>
      </div>

      <div className="flex items-center gap-4">
        <div className="flex items-center gap-2 text-sm">
          <div className="w-8 h-8 bg-amber-100 rounded-full flex items-center justify-center">
            <User size={16} className="text-amber-700" />
          </div>
          <span className="text-gray-700 font-medium">
            {user?.firstName} {user?.lastName}
          </span>
        </div>

        <button
          onClick={handleLogout}
          className="flex items-center gap-1.5 text-sm text-gray-500 hover:text-red-600 transition-colors"
        >
          <LogOut size={16} />
          Déconnexion
        </button>
      </div>
    </header>
  )
}
