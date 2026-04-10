import type { Transaction, TransactionType } from '@/types/transaction'

interface TransactionListProps {
  transactions: Transaction[]
  compact?: boolean
}

const typeLabels: Record<TransactionType, string> = {
  SEND: 'Envoi',
  RECEIVE: 'Réception',
  WITHDRAW: 'Retrait',
  DEPOSIT: 'Dépôt',
  PAYMENT: 'Paiement',
  AIRTIME: 'Crédit',
}

const typeIcons: Record<TransactionType, string> = {
  SEND: '↗️',
  RECEIVE: '↙️',
  WITHDRAW: '🏧',
  DEPOSIT: '💵',
  PAYMENT: '🛒',
  AIRTIME: '📞',
}

const typeColors: Record<TransactionType, string> = {
  SEND: 'text-red-600',
  RECEIVE: 'text-green-600',
  WITHDRAW: 'text-orange-600',
  DEPOSIT: 'text-green-600',
  PAYMENT: 'text-red-600',
  AIRTIME: 'text-purple-600',
}

function isDebit(type: TransactionType): boolean {
  return ['SEND', 'WITHDRAW', 'PAYMENT', 'AIRTIME'].includes(type)
}

export function TransactionList({ transactions, compact = false }: TransactionListProps) {
  if (transactions.length === 0) {
    return (
      <p className="text-gray-400 text-sm text-center py-8">
        Aucune transaction trouvée
      </p>
    )
  }

  return (
    <div className="space-y-2">
      {transactions.map((tx) => {
        const formatted = new Intl.NumberFormat('fr-BJ', {
          minimumFractionDigits: 0,
          maximumFractionDigits: 0,
        }).format(tx.amount)

        const date = new Date(tx.date)
        const dateStr = date.toLocaleDateString('fr-BJ', {
          day: '2-digit',
          month: 'short',
          year: compact ? undefined : 'numeric',
        })

        return (
          <div
            key={tx.id}
            className="flex items-center justify-between py-3 border-b border-gray-100 last:border-0"
          >
            <div className="flex items-center gap-3">
              <span className="text-xl">{typeIcons[tx.type]}</span>
              <div>
                <p className="text-sm font-medium text-gray-900">
                  {tx.description || typeLabels[tx.type]}
                </p>
                {!compact && tx.counterpartyName && (
                  <p className="text-xs text-gray-500">{tx.counterpartyName}</p>
                )}
                <p className="text-xs text-gray-400">{dateStr}</p>
              </div>
            </div>
            <div className="text-right">
              <p className={`text-sm font-semibold ${typeColors[tx.type]}`}>
                {isDebit(tx.type) ? '-' : '+'}{formatted} FCFA
              </p>
              {!compact && tx.fees > 0 && (
                <p className="text-xs text-gray-400">Frais: {tx.fees} FCFA</p>
              )}
            </div>
          </div>
        )
      })}
    </div>
  )
}
