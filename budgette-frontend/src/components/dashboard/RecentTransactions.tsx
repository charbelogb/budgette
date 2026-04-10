import type { Transaction } from '@/types/transaction'
import { TransactionList } from '@/components/transactions/TransactionList'

interface RecentTransactionsProps {
  transactions: Transaction[]
}

export function RecentTransactions({ transactions }: RecentTransactionsProps) {
  return (
    <div className="bg-white rounded-xl border border-gray-200 p-6">
      <h3 className="text-lg font-semibold text-gray-900 mb-4">Dernières transactions</h3>
      <TransactionList transactions={transactions} compact />
    </div>
  )
}
