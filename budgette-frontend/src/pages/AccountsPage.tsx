import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { Plus } from 'lucide-react'
import { accountService } from '@/services/accountService'
import { transactionService } from '@/services/transactionService'
import type { Account } from '@/types/account'
import { AccountCard } from '@/components/accounts/AccountCard'

export default function AccountsPage() {
  const [accounts, setAccounts] = useState<Account[]>([])
  const [loading, setLoading] = useState(true)
  const [syncing, setSyncing] = useState<string | null>(null)

  useEffect(() => {
    accountService.getAccounts()
      .then(setAccounts)
      .finally(() => setLoading(false))
  }, [])

  const handleSync = async (accountId: string) => {
    setSyncing(accountId)
    try {
      await transactionService.syncTransactions(accountId)
      const updated = await accountService.getAccounts()
      setAccounts(updated)
    } finally {
      setSyncing(null)
    }
  }

  if (loading) {
    return <div className="text-gray-500">Chargement...</div>
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Mes comptes</h1>
          <p className="text-gray-500 text-sm mt-1">
            {accounts.length} compte{accounts.length !== 1 ? 's' : ''} Mobile Money
          </p>
        </div>
        <Link
          to="/accounts/add"
          className="flex items-center gap-2 px-4 py-2 bg-amber-500 text-white text-sm font-medium rounded-lg hover:bg-amber-600 transition-colors"
        >
          <Plus size={16} />
          Ajouter un compte
        </Link>
      </div>

      {accounts.length === 0 ? (
        <div className="text-center py-20 bg-white rounded-xl border border-gray-200">
          <span className="text-4xl">📱</span>
          <h3 className="font-semibold text-gray-900 mt-4">Aucun compte ajouté</h3>
          <p className="text-gray-500 text-sm mt-2">
            Ajoutez votre premier compte MTN Mobile Money ou Moov Money
          </p>
          <Link
            to="/accounts/add"
            className="inline-flex items-center gap-2 mt-6 px-4 py-2 bg-amber-500 text-white text-sm font-medium rounded-lg hover:bg-amber-600 transition-colors"
          >
            <Plus size={16} />
            Ajouter un compte
          </Link>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {accounts.map((account) => (
            <AccountCard
              key={account.id}
              account={account}
              onSync={syncing === null ? handleSync : undefined}
            />
          ))}
        </div>
      )}
    </div>
  )
}
