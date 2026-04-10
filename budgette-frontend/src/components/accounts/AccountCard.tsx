import type { Account } from '@/types/account'

interface AccountCardProps {
  account: Account
  onSync?: (accountId: string) => void
}

const operatorColors: Record<string, string> = {
  MTN: 'bg-yellow-50 border-yellow-200 text-yellow-800',
  MOOV: 'bg-blue-50 border-blue-200 text-blue-800',
}

const operatorIcons: Record<string, string> = {
  MTN: '📱',
  MOOV: '📲',
}

export function AccountCard({ account, onSync }: AccountCardProps) {
  const colorClass = operatorColors[account.operator] || 'bg-gray-50 border-gray-200 text-gray-800'
  const icon = operatorIcons[account.operator] || '💳'

  const formatted = new Intl.NumberFormat('fr-BJ', {
    minimumFractionDigits: 0,
    maximumFractionDigits: 0,
  }).format(account.balance)

  return (
    <div className="bg-white rounded-xl border border-gray-200 p-6 hover:shadow-md transition-shadow">
      <div className="flex items-start justify-between mb-4">
        <div>
          <span className="text-2xl">{icon}</span>
          <h3 className="font-semibold text-gray-900 mt-1">{account.operatorDisplayName}</h3>
          <p className="text-sm text-gray-500">{account.phoneNumber}</p>
        </div>
        <span className={`px-2 py-1 rounded-full text-xs font-medium border ${colorClass}`}>
          {account.operator}
        </span>
      </div>

      <div className="mt-4">
        <p className="text-2xl font-bold text-gray-900">
          {formatted}
          <span className="text-sm font-normal text-gray-500 ml-1">{account.currency}</span>
        </p>
      </div>

      <div className="mt-4 flex items-center justify-between">
        <span className={`text-xs px-2 py-0.5 rounded-full ${account.active ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}`}>
          {account.active ? 'Actif' : 'Inactif'}
        </span>
        {onSync && (
          <button
            onClick={() => onSync(account.id)}
            className="text-xs text-amber-600 hover:text-amber-800 font-medium"
          >
            Synchroniser
          </button>
        )}
      </div>
    </div>
  )
}
