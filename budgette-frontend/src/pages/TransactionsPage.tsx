import { useEffect, useState } from 'react'
import { transactionService } from '@/services/transactionService'
import { accountService } from '@/services/accountService'
import type { Transaction, TransactionType } from '@/types/transaction'
import type { Account } from '@/types/account'
import { TransactionList } from '@/components/transactions/TransactionList'

const transactionTypes: { value: string; label: string }[] = [
  { value: '', label: 'Tous les types' },
  { value: 'SEND', label: 'Envois' },
  { value: 'RECEIVE', label: 'Réceptions' },
  { value: 'WITHDRAW', label: 'Retraits' },
  { value: 'DEPOSIT', label: 'Dépôts' },
  { value: 'PAYMENT', label: 'Paiements' },
  { value: 'AIRTIME', label: 'Crédit téléphonique' },
]

export default function TransactionsPage() {
  const [transactions, setTransactions] = useState<Transaction[]>([])
  const [accounts, setAccounts] = useState<Account[]>([])
  const [loading, setLoading] = useState(true)
  const [filterType, setFilterType] = useState('')
  const [filterAccount, setFilterAccount] = useState('')

  useEffect(() => {
    Promise.all([
      transactionService.getAllTransactions(),
      accountService.getAccounts(),
    ]).then(([txs, accs]) => {
      setTransactions(txs)
      setAccounts(accs)
    }).finally(() => setLoading(false))
  }, [])

  const filtered = transactions.filter((tx) => {
    if (filterType && tx.type !== filterType) return false
    if (filterAccount && tx.accountId !== filterAccount) return false
    return true
  })

  if (loading) {
    return <div className="text-gray-500">Chargement...</div>
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Transactions</h1>
        <p className="text-gray-500 text-sm mt-1">
          {filtered.length} transaction{filtered.length !== 1 ? 's' : ''}
        </p>
      </div>

      {/* Filters */}
      <div className="flex gap-3 flex-wrap">
        <select
          value={filterType}
          onChange={(e) => setFilterType(e.target.value)}
          className="px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-amber-500"
        >
          {transactionTypes.map((t) => (
            <option key={t.value} value={t.value}>{t.label}</option>
          ))}
        </select>

        <select
          value={filterAccount}
          onChange={(e) => setFilterAccount(e.target.value)}
          className="px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-amber-500"
        >
          <option value="">Tous les comptes</option>
          {accounts.map((a) => (
            <option key={a.id} value={a.id}>
              {a.operatorDisplayName} — {a.phoneNumber}
            </option>
          ))}
        </select>
      </div>

      {/* Transaction list */}
      <div className="bg-white rounded-xl border border-gray-200 p-6">
        <TransactionList transactions={filtered} />
      </div>
    </div>
  )
}
