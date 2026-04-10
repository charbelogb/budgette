import { useEffect, useState } from 'react'
import { transactionService } from '@/services/transactionService'
import type { DashboardData } from '@/types/transaction'
import { BalanceCard } from '@/components/dashboard/BalanceCard'
import { RecentTransactions } from '@/components/dashboard/RecentTransactions'
import { useAuthStore } from '@/store/authStore'

export default function DashboardPage() {
  const { user } = useAuthStore()
  const [data, setData] = useState<DashboardData | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    transactionService.getDashboard()
      .then(setData)
      .catch(() => setError('Impossible de charger le tableau de bord'))
      .finally(() => setLoading(false))
  }, [])

  if (loading) {
    return (
      <div className="flex items-center justify-center h-full">
        <div className="text-gray-500">Chargement...</div>
      </div>
    )
  }

  if (error) {
    return (
      <div className="p-4 bg-red-50 border border-red-200 rounded-lg text-red-700">
        {error}
      </div>
    )
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">
          Bonjour, {user?.firstName} 👋
        </h1>
        <p className="text-gray-500 text-sm mt-1">Voici un résumé de vos finances</p>
      </div>

      {/* Balance cards */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <BalanceCard
          title="Solde total"
          balance={data?.totalBalance ?? 0}
          currency={data?.currency ?? 'XOF'}
          icon="💰"
          className="md:col-span-1 bg-gradient-to-br from-amber-50 to-amber-100 border-amber-200"
        />
        {data?.accounts.slice(0, 2).map((account) => (
          <BalanceCard
            key={account.id}
            title={account.operatorDisplayName}
            balance={account.balance}
            currency={account.currency}
            operator={account.phoneNumber}
            icon={account.operator === 'MTN' ? '📱' : '📲'}
          />
        ))}
      </div>

      {/* Recent transactions */}
      {data?.recentTransactions && (
        <RecentTransactions transactions={data.recentTransactions} />
      )}
    </div>
  )
}
